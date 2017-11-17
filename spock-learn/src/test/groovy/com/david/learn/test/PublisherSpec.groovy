package com.david.learn.test

import com.david.learn.Publisher
import com.david.learn.Subscriber
import spock.lang.Specification

/**
 * Created by jiakang on 2017/10/31.
 */
class PublisherSpec extends Specification {

    Publisher publisher = new Publisher()
    Subscriber subscriber = Mock()
    Subscriber subscriber2 = Mock()

    def setup() {
        publisher.subscribers.add(subscriber)
        publisher.subscribers.add(subscriber2)
    }

    def "should send messages to all subsribers"() {
        when:
        publisher.send("hello")

        then:
        1 * subscriber.receive("hello")
        1 * subscriber2.receive("hello")
    }
}
