package com.tabbara.mohammad.trailedsheet;

/**
 * Created by Espfish on 11/6/2017.
 */

public interface TrailedSheetListeners {
    interface EventListener{
        void onExitUp(int id);
        void onExitDown(int id);
    }

    interface DragListener {
        void onDrag(int id);
    }
    interface ReleaseListener {
        void onUp(int id);
    }
    interface WhileAnimatingListener {
        void whileAnimatingUp(int id);
        void whileAnimatingDown(int id);
    }
}
