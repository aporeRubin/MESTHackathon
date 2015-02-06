<?php
namespace Edzi\Middleware;

class CsrfGuard extends \Slim\Extras\Middleware\CsrfGuard
{

    public function check() 
	{
		$path = $this->app->request()->getPath();
		$path_len = strlen($path);
		
		if(($path_len - strpos($path, '.json')) !== 5)
		{
			parent::check();
		}
	}

}