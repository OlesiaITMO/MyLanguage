package org.ITMO_Olesia.GC;

import java.util.ArrayList;
import java.util.List;
// Основной класс сборщика мусора
class GC {
    private final Heap heap;
    private final List<GCObject> roots; // Корневые объекты

    public GC(Heap heap) {
        this.heap = heap;
        this.roots = new ArrayList<>();
    }

    // Добавить корневой объект
    public void addRoot(GCObject obj) {
        roots.add(obj);
    }

    // Удалить корневой объект
    public void removeRoot(GCObject obj) {
        roots.remove(obj);
    }

    // Этап Mark: помечаем достижимые объекты
    private void mark(GCObject obj) {
        if (obj.marked) {
            return;
        }
        obj.marked = true;
        for (GCObject ref : obj.references) {
            mark(ref);
        }
    }

    // Этап Sweep and Compact: удаляем недостижимые объекты и устраняем фрагментацию
    private void sweepAndCompact() {
        int writeIndex = 0; // Указатель, куда перемещать живые объекты
        for (int readIndex = 0; readIndex < heap.objects.size(); readIndex++) {
            GCObject obj = heap.objects.get(readIndex);
            if (!obj.marked) {
                // Удаляем объект, который не помечен
                System.out.println("Collecting: " + obj.data);
            } else {
                // Сбрасываем метку и перемещаем живой объект
                obj.marked = false;
                heap.objects.set(writeIndex, obj);
                writeIndex++;
            }
        }
        // Удаляем все объекты после последнего записанного
        while (heap.objects.size() > writeIndex) {
            heap.objects.remove(heap.objects.size() - 1);
        }
    }

    // Полный цикл сборки мусора с компактированием
    public void collect() {
        // Шаг 1: Mark
        for (GCObject root : roots) {
            mark(root);
        }

        // Шаг 2: Sweep and Compact
        sweepAndCompact();
    }
}
