# **virtualball**

## General
This application have been developed in API 19.
The reason is that i have a iphone, and this was the only option i had at home.
I have tested almost all functionality on the API 28 too with Q, but if it any
problem with the ui or something else it will probably work fine on API 19

## Senors
This application is using the accelerometer sensor, to calculate the acceleration
of the simulated ball. Therefore i recommend you to have a phone with this sensor,
or the app will just send you to a error fragment.

## Fragments
The app is built up with fragments and one activity. I chose fragments because i 
wanted to test something i had not tried before, and will likely use this on the 
project.

## How to use the app
- First download the application from this repo
- Then install it on your android phone, warning (is developed for API 19)
- Then if the phone have the accelerometer you will see 3 buttons.
- Settings button you change the min value for what acceleration will be and detected
- Throw button will open up another tab and if u hit the start button you will be given
  6-5 seconds to perform one throw, will only register first throw.
- Score button will show ur results, and the first time using the app it should be empty
- Thats all.

The reason for introducing the 5 sek countdown was to give an element
of focus and more precise throws. The throw simulation will happend after the countdown. But 
the user have to do the throw in the interval of the countdown.

## The logic behind catch a throw
In the settings tab will we set a value of an acceleration min value. When we throw
the acceleration will go over this limit, and we take the 20 first reading after surpassing 
the limit set in settings. After that we get the max value of this array with 20 readings and 
we will have our acceleration.

## Math 
**Acceleration equation:**
```
ACC = sqrt(x*x + y*y + z*z) - EarthGravity

V^2 = v0^2 + 2a(r-r0)

V0 = ACC/time  when the ball leave the hand
```

## Tests
The test in this application is testing the mathUtils and database

## Database
The database i used in this app is the Room. And are used to saving the 
results of the scores

## Sound
Sound class i made playing a football kick after the calculations and reach the top
SoundUtils

## Extra
The recyclerview will show the top 5 highest ever scored throws.
And plays a sound when the top is reached
Assuming the throw happends after the 5 seconds, but the user has
to do the throw inside the interval

