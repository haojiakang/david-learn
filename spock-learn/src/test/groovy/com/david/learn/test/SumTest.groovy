package com.david.learn.test

import com.david.learn.Sum
import spock.lang.Specification

/**
 * Created by jiakang on 2017/10/31.
 */
class SumTest extends Specification {
    void setup() {
    }

    void cleanup() {
    }

    def sum = new Sum()

    def "sum should return param1+param2"() {
        expect:
            sum.sum(1, 2) == 3
    }
}
