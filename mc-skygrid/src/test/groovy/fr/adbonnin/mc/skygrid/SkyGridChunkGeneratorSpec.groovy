package fr.adbonnin.mc.skygrid

import fr.adbonnin.mc.skygrid.model.SkyGridWorld
import fr.adbonnin.mc.skygrid.model.block.MaterialBlockGroup
import org.bukkit.*
import org.bukkit.generator.ChunkGenerator
import org.junit.Ignore
import spock.lang.Specification
import spock.lang.Subject

import java.util.logging.Logger

class SkyGridChunkGeneratorSpec extends Specification {

    def mockRandom = Mock(Random)

    def skyGridWorld = new SkyGridWorld()

    def logger = Logger.getLogger(SkyGridChunkGeneratorSpec.name)

    def mockServer = Mock(Server) {
        getLogger() >> logger
    }

    def mockWorld = Mock(World)

    def mockPlugin = Mock(SkyGrid) {
        getLogger() >> logger
    }

    def mockBiomes = Mock(ChunkGenerator.BiomeGrid)

    @Subject
    def chunkGenerator = new SkyGridChunkGenerator(skyGridWorld, mockPlugin)

    void setup() {
        if (!Bukkit.server) {
            Bukkit.server = mockServer
        }
    }

    void "should generate chunk data"() {
        given:
        def chunkData = Mock(ChunkGenerator.ChunkData)

        skyGridWorld.setHeight(height)
        skyGridWorld.addBlockGroup(1, new MaterialBlockGroup(material))

        when:
        def result = chunkGenerator.generateChunkData(mockWorld, mockRandom, 0, 0, mockBiomes)

        then:
        1 * mockServer.createChunkData(mockWorld) >> chunkData
        16 * mockRandom.nextDouble() >> 0.1
        16 * chunkData.setBlock(_, _, _, material)
        result == chunkData

        where:
        height = 1
        material = Material.BEDROCK
    }

    @Ignore
    void "should get fixed spawn location"() {
        given:
        skyGridWorld.setHeight(height)

        expect:
        chunkGenerator.getFixedSpawnLocation(mockWorld, mockRandom) == new Location(mockWorld, 0.5D, expectedSpawnLocationY, 0.5D)

        where:
        height || expectedSpawnLocationY
        4      || 1
        5      || 5
        128    || 125
    }
}
