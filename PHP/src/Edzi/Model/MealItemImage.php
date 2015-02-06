<?php

namespace Edzi\Model;

class MealItemImage extends \Illuminate\Database\Eloquent\Model
{
	protected $guarded = array('id');
	public $timestamps = false;
	protected $table 	= 'meal_item_images';
	
    protected $appends = array('url');
    
	public function getUrlAttribute()
    {
        return \Edzi\Util\App::get()->configula->app['assets'] . 'images/' . $this->name;    
    }
	
	
}