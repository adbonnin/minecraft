package fr.adbonnin.mc.skygrid.model

import spock.lang.Specification
import spock.lang.Unroll

class SkyGridWorldSpec extends Specification {

    @Unroll
    void "should get highest block index #expectedIndex for height #height"() {
        given:
        def world = new SkyGridWorld()
        world.height = height

        expect:
        world.highestBlockY == expectedIndex

        where:
        height || expectedIndex
        0      || 0
        1      || 0
        4      || 0
        5      || 4
        8      || 4
        9      || 8
    }
}
