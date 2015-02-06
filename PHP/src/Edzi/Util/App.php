<?php

namespace Edzi\Util;

class App
{	
	protected static $config;
	
	public static function set($conf)
	{
		self::$config = $conf;
	}	
	
	public static function get()
	{
		return self::$config;
	}	
	
}
