<?php
$opts = [
	'scheme' => '',
	'host' => '',
	'path' => '',
	'user' => '',
	'pass' => '',
];

if(getenv('CLEARDB_DATABASE_URL'))
{
	$opts = parse_url(getenv('CLEARDB_DATABASE_URL'));
}

$config = array ();

$config['database'] = [
    'driver'    => $opts['scheme'],
    'host'      => $opts['host'],
    'database'  => ltrim($opts['path'], '/'),
    'username'  => $opts['user'],
    'password'  => $opts['pass'],
    'charset'   => 'utf8',
    'collation' => 'utf8_unicode_ci',
    'prefix'    => '',
];
