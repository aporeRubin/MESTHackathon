<?php

namespace Edzi\Util;

class UID
{	
	public static function generate($hasher = 'md5')
	{
		$str = gethostname() . '_' . bin2hex(openssl_random_pseudo_bytes(32));
		return hash($hasher, $str);
	}	
	
}
