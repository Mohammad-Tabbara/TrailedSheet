# TrailedSheet

[![](https://jitpack.io/v/Mohammad-Tabbara/TrailedSheet.svg)](https://jitpack.io/#Mohammad-Tabbara/TrailedSheet)

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
	compile 'com.github.mohammad-tabbara:trailedsheet:0.0.5'
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

Coming Soon

### In Code(Methods):

```
trailedSheet.lock(); //Disables TouchEvents(Parent and Children)
trailedSheet.unlock(); //Enables TouchEvents(Parent and Children)
trailedSheet.unlocked(); //Check Lock Status
trailedSheet.moveUp(); // animates moveup and triggers actionController Up
trailedSheet.moveDown(); // animates movedown and triggers actionController down
```


## Future work

Coming Soon
