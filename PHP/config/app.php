<?php
$config = array ();

$config['app'] = [
	'debug' 				=> true,
	'mode' 					=> 'production',
	'cookies.encrypt' 		=> true,
	'cookies.secure' 		=> isset($_SERVER) ? (bool) ($_SERVER['REQUEST_SCHEME'] == 'https') : false,
	'cookies.lifetime' 		=> 0,
	'cookies.httponly' 		=> true,
	'log.enabled' 			=> true,
	'log.level' 			=> \Slim\Log::ERROR,
	'log.recipients' 		=> [ ['type' => 'to', 'email' => 'sfroelich01@gmail.com'], ],
	'sessions.enabled'		=> true,
	'assets'				=> 'https://s3-eu-west-1.amazonaws.com/edzibanpa/',
];