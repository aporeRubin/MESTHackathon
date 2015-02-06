<?php

//initiate load
require __DIR__ . '/../bootstrap.php';

use Illuminate\Database\Capsule\Manager as DB;
use Edzi\Model as Model;

//api web group
$app->group('/api', function () use ($app) {
	include __DIR__ . '/api.php';

});


//TODO: validation of input
//TODO: security

$app->get('/', function() use ($app) {
	$app->render('index.twig');	
});

$app->get('/meal_items', function() use ($app) {
	
	$app->render('meals.twig', ['meals' => Model\MealItem::all()]);
	
});
$app->get('/meal_items/:id', function($id) use ($app) {

	
	$rel1 = DB::table('related_meal_items')
				->select('meal_two_id')
				->where('meal_one_id', $id)
				->lists('meal_two_id');
	$rel2 = DB::table('related_meal_items')
				->select('meal_one_id')
				->where('meal_two_id', $id)
				->lists('meal_one_id');
	
	$rel = array_merge($rel1, $rel2);
			
			
	$app->render('meal_item.twig', [
			'meal' => Model\MealItem::with('images')->findOrFail($id), 
			'other_meals' => Model\MealItem::whereNotIn('id', array_merge($rel, [$id]))->get(),
			'related_meals' => $rel ? Model\MealItem::whereIn('id', $rel)->get() : [],
	]);
	
});
$app->post('/meal_items/:id', function($id) use ($app) {
	
	$input = $app->request->post();
	unset($input['csrf_token']);
	Model\MealItem::findOrFail($id)->update($input);
	
	$app->flash('success', 'Updated!');
	
	$app->redirect('/meal_items/' . $id);
});

$app->put('/meal_images', function() use ($app) {
	$id = $app->request->put('meal_item_id');
	
	if(isset($_FILES['image']) && $_FILES['image']['error'] == UPLOAD_ERR_OK && is_uploaded_file($_FILES['image']['tmp_name'])) { }
	else $app->redirect('/meal_items/' . $id);
	
	$filename = Edzi\Util\UID::generate() . '.' . pathinfo($_FILES['image']['name'], PATHINFO_EXTENSION);
	
	if($app->configula->app['mode'] == 'production') 
	{
		$s3 = Aws\S3\S3Client::factory(array(
			'key'    => $app->configula->aws['key'],
			'secret' => $app->configula->aws['secret'],
		));
		$s3->setRegion($app->configula->aws['default_region']);
		$result = $s3->putObject(array(
			'Bucket'       => $app->configula->aws['bucket'],
			'Key'          => 'images/' . $filename,
			'SourceFile'   => $_FILES['image']['tmp_name'],
			'ACL'          => 'public-read',
			'StorageClass' => 'STANDARD',
			'CacheControl' 	=> 'public, max-age=31536000',
		));
	}
	else
	{
		$dir = APP_DIR . $app->configula->app['assets'] . '/images/';
		if(!move_uploaded_file($_FILES['image']['tmp_name'], $dir . $filename)) 
		{
			$app->flash('error', 'Failed to upload file');
			$app->redirect('/meal_items/' . $id);
		}
	}
	
	$image = new Model\MealItemImage;
	$image->name = $filename;
	Model\MealItem::findOrFail($id)->images()->save($image);
	//TODO remove actual image from storage
	$app->flash('success', 'Image Uploaded!');
	
	$app->redirect('/meal_items/' . $id);
});
$app->delete('/meal_images/:id', function($id) use ($app) {
	$image = Model\MealItemImage::findOrFail($id);
	$pid = $image->meal_item_id;
	$image->delete();
	
	$app->flash('success', 'Image deleted successfully');
	$app->redirect('/meal_items/' . $pid);
});


$app->put('/related_meals', function() use ($app) {
	$id = $app->request->put('meal_item_id');
	$id2 = $app->request->put('meal_item_id2');
	
	$exists = DB::table('related_meal_items')
			->where(function($q) use ($id, $id2)  {
				$q->where('meal_one_id', $id)->where('meal_two_id', $id2);
			})
			->orWhere(function($q) use ($id, $id2) {
				$q->where('meal_two_id', $id)->where('meal_one_id', $id2);
			})
			->first();
	if(!$exists)
		DB::table('related_meal_items')->insert(
			['meal_one_id' => $id, 'meal_two_id' => $id2]
		);
	
	$app->flash('success', 'Relationship added');
	$app->redirect('/meal_items/' . $id);
});
$app->delete('/related_meals', function() use ($app) {
	$id = $app->request->post('meal_item_id');
	$id2 = $app->request->post('meal_item_id2');
	
	DB::table('related_meal_items')
			->where(function($q) use ($id, $id2)  {
				$q->where('meal_one_id', $id)->where('meal_two_id', $id2);
			})
			->orWhere(function($q) use ($id, $id2) {
				$q->where('meal_two_id', $id)->where('meal_one_id', $id2);
			})->delete();
		
	$app->flash('success', 'Relationship deleted successfully');
	$app->redirect('/meal_items/' . $id);
});

$app->get('/add_meal', function() use ($app) {
	$app->render('meal_item.twig');
	
});
$app->post('/add_meal', function() use ($app) {
	$input = $app->request->post();
	unset($input['csrf_token']);
	Model\MealItem::create($input);
	
	$app->redirect('/meal_items');
});

$app->get('/run_script', function() use ($app) {
	$app->render('run_script.twig');
	
});
$app->post('/run_script', function() use ($app) {
	
	DB::transaction(function() use ($app)
	{
		DB::statement($app->request->post('script'));
	});   
	
	$app->redirect('/run_script');
	
});


$app->run();