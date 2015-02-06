<?php

namespace Edzi\Model;

class MealItem extends \Illuminate\Database\Eloquent\Model
{
	protected $guarded = array('id');
	public $timestamps = false;
	protected $table 	= 'meal_items';
	
    protected $appends = array('image_url');

	public function images()
    {
        return $this->hasMany('\\Edzi\Model\\MealItemImage');
    }
	
    public function getImageUrlAttribute()
    {
		$im = $this->images()->first();
        return $im ? \Edzi\Util\App::get()->configula->app['assets'] . 'images/' . $im->name : '';    
    }
}