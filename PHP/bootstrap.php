<?php

define('ROOT_DIR', __DIR__ . '/');
define('APP_DIR', ROOT_DIR . 'web/');

//include the autoloader
require ROOT_DIR . 'vendor/autoload.php';

//configure slim
$app = new \Slim\Slim();
Edzi\Util\App::set($app);

$app->base_url = $app->environment['slim.url_scheme'] . '://' . $app->environment['HTTP_HOST'];
//setup config resource
$app->container->singleton('configula', function () {
	$config = new Configula\Config(ROOT_DIR . 'config');
	$config->loadConfig(ROOT_DIR . 'config/');

	return $config;
});

$app->container->singleton('user', function () use ($app) {
	return $app->auth->user();;
});

//set configuration
$configuration = $app->configula->app;
$configuration['templates.path'] 	=  ROOT_DIR . 'web/templates';

//configure sessions
if($configuration['sessions.enabled'])
{
	session_cache_limiter(false);
	session_start();
	register_shutdown_function('session_write_close');
}


//configure logging
$handlers = [new \Monolog\Handler\StreamHandler( ROOT_DIR . 'logs/'.date('Y-m-d').'.log')];
$processors = [];
//$processors[] = new \Monolog\Processor\IntrospectionProcessor;
$processors[] = new \Monolog\Processor\WebProcessor;
	
if($configuration['mode'] == 'production')
{
	$mailHandler = new \Edzi\Logger\MandrillHandler($app->configula->mandrill['key'], ['name' => 'Logger (EDZIBAN)', 'email' => 'logs@edzibanpa.com'], $configuration['log.recipients']);
	$mailHandler->setFormatter(new \Monolog\Formatter\HtmlFormatter);
	$handlers[] = $mailHandler;
}
$configuration['log.writer'] 	    = new \Flynsarmy\SlimMonolog\Log\MonologWriter(array(
											'handlers' => $handlers,
                                            'processors' => $processors,
										));

//configure twig										
$view = new \Slim\Views\Twig();
$view->set('assets', $configuration['assets']);
$view->set('configuration', $configuration);
$view->parserOptions = array(
    'cache' => ROOT_DIR . 'cache/',
	'debug' => $configuration['debug'],
);
$view->parserExtensions = array(
    new \Slim\Views\TwigExtension(),
	new \Twig_Extension_Debug(),
);
$configuration['view'] 	= $view;


//configure the application
$app->config($configuration);

if($configuration['sessions.enabled'])//sessions required for this
{
	//add the csrf middleware
	$app->add(new \Edzi\Middleware\CsrfGuard());
}

//add the whoops error handler
$app->add(new \Zeuxisoo\Whoops\Provider\Slim\WhoopsMiddleware);

//add notfound handler
$app->notFound(function () use ($app) {
    $app->render('404.twig');
});

//add error handler
$app->error(function (\Exception $e) use ($app, $configuration) {
	$app->log->error($e);
	$app->render('500.twig');
});


//setup the database
use Illuminate\Database\Capsule\Manager as Capsule;
use Illuminate\Events\Dispatcher;
use Illuminate\Container\Container;

$capsule = new Capsule;
$capsule->addConnection($app->configula['database']);
//configure dispatcher
$capsule->setEventDispatcher(new Dispatcher(new Container));
// Make this Capsule instance available globally via static methods
$capsule->setAsGlobal();
$capsule->bootEloquent();
if($configuration['mode'] == 'production')
{
    Capsule::connection()->disableQueryLog();
}


