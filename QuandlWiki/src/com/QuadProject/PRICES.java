package com.QuadProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PRICES {

@SerializedName("datatable")
@Expose
private Datatable datatable;
@SerializedName("meta")
@Expose
private Meta meta;

public Datatable getDatatable() {
return datatable;
}

public void setDatatable(Datatable datatable) {
this.datatable = datatable;
}

public Meta getMeta() {
return meta;
}

public void setMeta(Meta meta) {
this.meta = meta;
}

}