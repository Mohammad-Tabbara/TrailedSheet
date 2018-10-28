TOBE CONTINUED WHEN FREE
# TrailedSheet

## Download

1. Add the repositories is root(build.gradle):

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

2. Add the dependency in app(build.gradle):

```
dependencies {
	implementation 'com.github.Mohammad-Tabbara:TrailedSheet:1.0'
}
```


## Usage

### In XML(Init):

```
<com.tabbara.mohammad.trailedsheet.TrailedSheet
    android:id="@+id/trailed_sheet"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FF0000">
    ...
</com.tabbara.mohammad.trailedsheet.TrailedSheet>
```

### TouchEvents:

Fling Up or down to trigger an EventInterface.

Drag Up or Down If The Drag exceeds half its hieght it will Trigger an EventInterface.

### In Code(Methods):

```
trailedSheet.lock(); //Disables TouchEvents(Parent and Children)
trailedSheet.unlock(); //Enables TouchEvents(Parent and Children)
trailedSheet.unlocked(); //Check Lock Status
trailedSheet.moveUp(); // animates moveup and triggers eventListener Up
trailedSheet.moveDown(); // animates movedown and triggers eventListener down
```

### TrailedSheetListeners:

*ID is the id of the view

1. interface EventListener

    void onExitUp(int id)
    
    void onExitDown(int id)
    
2. DragListener:

    void onDrag(int id)
    
3. ReleaseListener:

    void onUp(int id)
    
4. WhileAnimatingListener:

    void whileAnimatingUp(int id)
    
    void whileAnimatingDown(int id)


## Future work

Drag Orientation. In Xml Attributes.
