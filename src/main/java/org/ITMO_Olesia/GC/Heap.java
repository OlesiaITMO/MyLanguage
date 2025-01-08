package org.ITMO_Olesia.GC;

import java.util.ArrayList;
import java.util.List;

class Heap {
    List<GCObject> objects;           // Список всех объектов на куче

    public Heap() {
        this.objects = new ArrayList<>();
    }

    public GCObject allocate(String data) {
        GCObject obj = new GCObject(data);
        objects.add(obj);
        return obj;
    }
}
