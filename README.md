# ImageMatch
ImageMatch tries to evaluate whether one image is a sub image of another image
It support a diverse set of matching algorithms from a simple full search to a histogram approach as well as keypoint based matching.


## Examples
[corr matcher example] (src/com/sleepycoders/imagematch/matcher/CorrelationMatcher.java)
usage: java imagematch matcher corr src.image out.image sub.image
you can use different metrics and likeness thresholds for the correlation matcher
it will produce a list of matching candidates sorted desc sorted by likeness
results: test-sub.png 	offset: 204x181 size: 312x280

<img src="doc/img/test-src.png" width="45%"></img> <img src="doc/img/test-sub-out.png" width="45%"></img>


[sepia filter example] (src/com/sleepycoders/imagematch/effect/Effect.java)
usage: java imagematch filter sepia src.image out.image

<img src="doc/img/test-src.png" width="45%"></img> <img src="doc/img/test-sepia-out.png" width="45%"></img>


## Matchers
- corr: can be used with a custom metric + likeness threshold
- naiv: runs an exact matcher on each window slice
- exact: compares two images for equality


## Metrics:
- ColorDifference: calculates the absolute sum of difference for all color channels
- NormalizedColorDifference: maps the absolute color difference into a [0,1] range
- NormalizedEqualityDifference: calculates the difference of each pixels color channel


## Filters:
- sepia: applys a sepia filter to the image
- red: extracts the red color channel from an image
- green: extracts the green color channel from an image
- blue: extracts the blue color channel from an image


## Disclaimer
Published under the [MIT License](LICENSE).
