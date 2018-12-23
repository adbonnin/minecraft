package fr.adbonnin.mc.skygrid.mapper


import fr.adbonnin.xtra.bukkit.yaml.ObjectNode
import fr.adbonnin.xtra.bukkit.yaml.YamlNode
import fr.adbonnin.xtra.io.XtraIO
import org.bukkit.configuration.file.YamlConfiguration
import spock.lang.Specification
import spock.lang.Subject

class ConfigMapperSpec extends Specification {

    @Subject
    def mapper = ConfigMapper.INSTANCE

    void "should map configuration"() {
        given:
        def config = readDefaultConfig()
        def map = mapper.map(config)

        expect:
        true
    }

    static YamlNode readDefaultConfig() {
        def input = ConfigMapperSpec.getResourceAsStream('/config.yml')
        def reader = null
        try {
            reader = new InputStreamReader(input)
            def configuration = YamlConfiguration.loadConfiguration(reader)
            return new ObjectNode(configuration)
        }
        finally {
            XtraIO.closeQuietly(reader)
        }
    }
}
