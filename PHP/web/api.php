<?php

use Illuminate\Database\Capsule\Manager as DB;
use Edzi\Model as Model;


function send_json($app, $data, $cache_control_secs = 600) {
	$app->response->headers->set('Content-Type', 'application/json');
	$app->response->headers->set('Cache-Control', 'public, max-age=' . $cache_control_secs);
	echo json_encode($data);
}

$app->get('/search.json', function() use ($app) {
		
	$query = $app->request->get('query');
	
	$meal_type = $app->request->get('X-EDZI-MEAL-TYPE');
	$is_diabetic = $app->request->get('X-EDZI-DIABETIC');
	$is_hypertensive = $app->request->get('X-EDZI-HYPERTENSIVE');
	$is_losing_weight = $app->request->get('X-EDZI-LOSING-WEIGHT');
	$is_recuperating = $app->request->get('X-EDZI-RECUPERATING');
	$is_lactose_intolerant = $app->request->get('X-EDZI-LACTOSE-INTOLERANT');
	$activity_level = $app->request->get('X-EDZI-ACTIVITY-LEVEL');
	
	$meals = Model\MealItem::where('name', 'LIKE', "%{$query}%")->orderBy('general_rating', 'DESC');
	
	switch($meal_type) 
	{
		case 'breakfast':
			$meals->where('is_breakfast', '!=', '0');
			break;
		case 'lunch':
			$meals->where('is_lunch', '!=', '0');
			break;
		case 'supper':
			$meals->where('is_supper', '!=', '0');
			break;
		case 'snack':
			$meals->where('is_snack', '!=', '0');
			break;
	}
	 
	if($is_diabetic)
		$meals->where('diabetic_rating', '>', '5');
	if($is_hypertensive)
		$meals->where('hypertensive_rating', '>', '5');
	if($is_recuperating)
		$meals->where('recuperation_rating', '>', '5');
	if($is_losing_weight)
		$meals->where('weightloss_rating', '>', '5');
	if($is_lactose_intolerant)
		$meals->where('contains_lactose', '!=', '1')->orderBy('fibre_rating', 'DESC');
	
	if(in_array($activity_level, ['active', 'athletic']))
		$meals->orderBy('calories_per_portion', 'DESC');
	else if(in_array($activity_level, ['sedentary', 'moderate']))
		$meals->orderBy('calories_per_portion', 'ASC');
	
    send_json($app, $meals->get());
});

$app->get('/meals.json', function() use ($app) {
		
	$meal_type = $app->request->get('X-EDZI-MEAL-TYPE');
	$is_diabetic = $app->request->get('X-EDZI-DIABETIC');
	$is_hypertensive = $app->request->get('X-EDZI-HYPERTENSIVE');
	$is_losing_weight = $app->request->get('X-EDZI-LOSING-WEIGHT');
	$is_recuperating = $app->request->get('X-EDZI-RECUPERATING');
	$is_lactose_intolerant = $app->request->get('X-EDZI-LACTOSE-INTOLERANT');
	$activity_level = $app->request->get('X-EDZI-ACTIVITY-LEVEL');
	
	$meals = Model\MealItem::orderBy('general_rating', 'DESC');

	switch($meal_type) 
	{
		case 'breakfast':
			$meals->where('is_breakfast', '!=', '0');
			break;
		case 'lunch':
			$meals->where('is_lunch', '!=', '0');
			break;
		case 'supper':
			$meals->where('is_supper', '!=', '0');
			break;
		case 'snack':
			$meals->where('is_snack', '!=', '0');
			break;
	}
	
	if($is_diabetic)
		$meals->where('diabetic_rating', '>', '5');
	if($is_hypertensive)
		$meals->where('hypertensive_rating', '>', '5');
	if($is_recuperating)
		$meals->where('recuperation_rating', '>', '5');
	if($is_losing_weight)
		$meals->where('weightloss_rating', '>', '5');
	if($is_lactose_intolerant) 
		$meals->where('contains_lactose', '!=', '1')->orderBy('fibre_rating', 'DESC');
	
	if(in_array($activity_level, ['active', 'athletic']))
		$meals->orderBy('calories_per_portion', 'DESC');
	else if(in_array($activity_level, ['sedentary', 'moderate']))
		$meals->orderBy('calories_per_portion', 'ASC');
	
	
    send_json($app, $meals->get());
});

$app->get('/meals/:id.json', function($id) use ($app) {
	$meal = Model\MealItem::with('images')->find($id);
	
	if(!$meal)
		$app->halt(404, 'Meal cannot be found');
		
    send_json($app, $meal);
});

