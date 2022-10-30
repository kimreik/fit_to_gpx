# fit_to_gpx
Simple `.fit` to `.gpx` converter.  

Takes 1 or more string paths to the source files, creates the result files in the same directories, with the same names, but `.gpx` extension.  

That's it!  

Example of usage:
```Kotlin
   val conversionResults: List<ConversionResult> = FitToGpx().convert(
            "valid_training.fit",
            "non_existent_file.fit"
        )

    conversionResults[0].success()  //true
    conversionResults[0].targetPath //"valid_training.gpx"
    conversionResults[0].error      //null

    conversionResults[1].success()  //false
    conversionResults[1].targetPath //null
    conversionResults[1].error      //ConversionError.FILE_DOES_NOT_EXIST
```
Standalone jar:
```
java -jar .\fit_to_gpx-1.0-SNAPSHOT-standalone.jar valid_training.fit non_existent_file.fit wrong_extension.txt corrupted_fit_file.fit
valid_training.fit : SUCCESS: valid_training.gpx
non_existent_file.fit : FAILED: Source file does not exists
wrong_extension.txt : FAILED: Source file should have 'fit' extension
corrupted_fit_file.fit : FAILED: Failed to read from the source file
```

Fit file decoding is based on Garmin SDK. Unfortunately, Garmin doesn't provide it in any conventional way.  
So, to run this project locally, you'll have to download the SDK from  
https://developer.garmin.com/fit/download/  
and put the source files under `src/main/java/`
