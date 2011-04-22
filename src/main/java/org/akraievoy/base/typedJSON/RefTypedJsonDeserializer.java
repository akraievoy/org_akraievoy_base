/*
 Copyright 2011 Anton Kraievoy akraievoy@gmail.com
 This file is part of org.akraievoy:base.

 org.akraievoy:base is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 org.akraievoy:base is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with org.akraievoy:base. If not, see <http://www.gnu.org/licenses/>.
 */

package org.akraievoy.base.typedJSON;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class RefTypedJsonDeserializer extends JsonDeserializer<RefTypedJson> {
  @Override
  @SuppressWarnings({"unchecked"})
  public RefTypedJson deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    final RefTypedJson refTyoedJson = new RefTypedJson(null);
    Class vClass = null;

    jp.nextToken(); // will return JsonToken.START_OBJECT
    while (jp.nextToken() != JsonToken.END_OBJECT) {
      String fieldname = jp.getCurrentName();
      if ("vClass".equals(fieldname)) {
        try {
          vClass = Class.forName(jp.getText());
        } catch (ClassNotFoundException e) {
          throw new IOException(e);
        }
      } else if ("v".equals(fieldname)) {
        jp.nextValue();
        final Object v = jp.readValueAs(vClass);
        refTyoedJson.setValue(v);
      } else {
        throw new IllegalStateException("Unrecognized field '" + fieldname + "'!");
      }
    }

    return refTyoedJson;
  }
}
