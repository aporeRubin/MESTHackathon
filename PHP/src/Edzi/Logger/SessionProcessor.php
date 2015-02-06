<?php

/*
 * This file is part of the Monolog package.
 *
 * (c) Jordi Boggiano <j.boggiano@seld.be>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

namespace Edzi\Logger;

/**
 * Injects url/method and remote IP of the current web request in all records
 *
 * @author Jordi Boggiano <j.boggiano@seld.be>
 */
class SessionProcessor
{

    /**
     * @param mixed $serverData array or object w/ ArrayAccess that provides access to the $_SERVER data
     */
    public function __construct()
    {
    }

    /**
     * @param  array $record
     * @return array
     */
    public function __invoke(array $record)
    {

        $record['extra'] = array_merge(
            $record['extra'],
            array(
                'session'     => json_encode($_SESSION),
            )
        );

        return $record;
    }
}
