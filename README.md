```kotlin
Coma.withActivity(activityContext)
    .with(requestCode, Intent(Intent.ACTION_GET_CONTENT))
    .withRequestHandler { result -> /* handle startActivityForResult here */ }
    .startForResult()
```