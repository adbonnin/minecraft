package fr.adbonnin.mc.skygrid

import spock.lang.Specification

class XtraSkyGridSpec extends Specification {

    void "should iterate on block chunks"() {
        given:
        def function = Mock(LocationFunction)

        when:
        XtraSkyGrid.chunkIteration(height, function)

        then:
        1 * function.apply(0, 0, 0)
        1 * function.apply(0, 0, 4)
        1 * function.apply(0, 0, 8)
        1 * function.apply(0, 0, 12)

        1 * function.apply(4, 0, 0)
        1 * function.apply(4, 0, 4)
        1 * function.apply(4, 0, 8)
        1 * function.apply(4, 0, 12)

        1 * function.apply(8, 0, 0)
        1 * function.apply(8, 0, 4)
        1 * function.apply(8, 0, 8)
        1 * function.apply(8, 0, 12)

        1 * function.apply(12, 0, 0)
        1 * function.apply(12, 0, 4)
        1 * function.apply(12, 0, 8)
        1 * function.apply(12, 0, 12)

        where:
        height = 1
    }
}
