package com.QuadProject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datatable {

@SerializedName("data")
@Expose
private List<List<String>> data = null;
@SerializedName("columns")
@Expose
private List<Column> columns = null;

public List<List<String>> getData() {
return data;
}

public void setData(List<List<String>> data) {
this.data = data;
}

public List<Column> getColumns() {
return columns;
}

public void setColumns(List<Column> columns) {
this.columns = columns;
}

}