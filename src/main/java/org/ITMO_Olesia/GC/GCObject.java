package org.ITMO_Olesia.GC;

import java.util.ArrayList;
import java.util.List;

class GCObject {
    String data;                      // Данные объекта
    List<GCObject> references;        // Ссылки на другие объекты
    boolean marked;                   // Метка для алгоритма Mark-and-Sweep

    public GCObject(String data) {
        this.data = data;
        this.references = new ArrayList<>();
        this.marked = false;
    }
}
