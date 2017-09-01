package com.QuadProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

@SerializedName("next_cursor_id")
@Expose
private Object next_cursor_id;

public Object getnext_cursor_id() {
return next_cursor_id;
}

public void setnext_cursor_id(Object next_cursor_id) {
this.next_cursor_id = next_cursor_id;
}

}