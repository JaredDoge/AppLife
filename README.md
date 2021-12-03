# AppLife
[![](https://jitpack.io/v/JaredDoge/AppLife.svg)](https://jitpack.io/#JaredDoge/AppLife)

將ProcessLifecycleOwner封裝成background/foreground callback
# Getting started
1. Add it in your root build.gradle at the end of repositories:
   ```gradle
    allprojects {
      repositories {
        // ...
        maven { url 'https://jitpack.io' }
      }
    }
   ```
2. Add the dependency
   ```gradle
   dependencies {
	        implementation 'com.github.JaredDoge:AppLife:{latest_version}'
	 }
   ```  
# Initialization
在你的Application初始化AppLife
```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        
        AppLife.init(this)
    }
}
```
>別忘記在你的AndroidManifest.xml裡註冊你的Application

# Usage
#### background/foreground callback
```kotlin
AppLife.get().addCallback(object :AppLife.Callback{
            override fun appToBackground() {
                super.appToBackground()
            }

            override fun appToForeground() {
                super.appToForeground()
            }
})
```
>在不需要監聽時，調用removeCallback()

你也可以手動檢查目前在前景還是背景

```kotlin
AppLife.get().isInForeground()

AppLife.get().isInBackground()
```
#### TopActivity
調用getTopActivity()可以取得目前在Task頂端的activity
```kotlin
AppLife.get().getTopActivity()
```
#### Release
```kotlin
AppLife.release()
``` 








