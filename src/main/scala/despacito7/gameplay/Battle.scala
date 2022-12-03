import despacito7.ResourceLoader;
import despacito7.Constants;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;

package despacito7.gameplay {
    class Move(val name: String) extends Enumeration {
        private val stats: Map[Constants.Stat,Float];
        
        def this(val entry: Map.Entry[String, JsonElement]) {
            this(entry.getKey())
            this.stats = new Map[Constants.Stat,Float]();
            for (stat <- Constants.Stat.values())
                this.stats.put(stat, entry.getValue().getAsJsonObject().get(stat.name().toLowerCase()).getAsFloat());
        }
    }
}