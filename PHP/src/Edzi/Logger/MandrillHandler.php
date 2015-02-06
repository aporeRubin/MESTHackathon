<?php

namespace Edzi\Logger;

class MandrillHandler extends \Monolog\Handler\MailHandler
{
    protected $key;
    protected $from;
    protected $to;

    /**
     * @param string           		  $key     Mandril Key
     * @param array					  $from    An array containing the name and email
     * @param array                   $to      An array of recipients' email
     * @param integer                 $level   The minimum logging level at which this handler will be triggered
     * @param Boolean                 $bubble  Whether the messages that are handled can bubble up the stack or not
     */
    public function __construct($key, array $from, array $to, $level = \Monolog\Logger::ERROR, $bubble = true)
    {
        parent::__construct($level, $bubble);
		
        $this->key  = $key;
        $this->from  = $from;
        $this->to  = $to;
    }

    /**
     * {@inheritdoc}
     */
    protected function send($content, array $records)
    {
		try
		{
			$mandrill = new \Mandrill($this->key);
				
			$message = [
				'html'			=> $content,
				'subject' 		=> $records[0]['level_name'],
				'from_email' 	=> $this->from['email'],
				'from_name' 	=> $this->from['name'],
				'to' 			=> $this->to,
				'important' 	=> true,
				'auto_text' 	=> true,
				'inline_css' 	=> true,
				"track_opens" 	=> false,
				"track_clicks" 	=> false,
			];
				
			$mandrill->messages->send($message);//fingers crossed
		}
		catch(\Exception $e)
		{
		
		}
    }
}