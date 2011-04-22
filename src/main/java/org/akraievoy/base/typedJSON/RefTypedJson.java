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

import org.akraievoy.base.ref.RefSimple;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializable;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.IOException;

/**
 * The way to embed type information into serialized JSON.
 *
 * @param <T> stored value type.
 */
@JsonDeserialize(using = RefTypedJsonDeserializer.class)
public class RefTypedJson<T> extends RefSimple<T> implements JsonSerializable {
  protected String vClass;

  public RefTypedJson(T v) {
    super(v);
    updateVClass(v);
  }

  protected void updateVClass(Object v) {
    vClass = v != null ? v.getClass().getName() : null;
  }

  @Override
  public T getValue() {
    return super.getValue();
  }

  @Override
  public void setValue(T v) {
    super.setValue(v);
    updateVClass(v);
  }

  public String getVClass() {
    return vClass;
  }

  public void setVClass(String vClass) {
    this.vClass = vClass;
  }

  public void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException {
    jgen.writeStartObject();
    jgen.writeStringField("vClass", vClass);
    jgen.writeObjectField("v", v);
    jgen.writeEndObject();
  }
}
