# testWebRTC
test webvrtc from an Android webview

To build the project :
- set gradle.properties on the correct proxy address and port
- excute gradlew :
	cd TestWebRTC/
	./gradlew assembleDebug


To test the WebRTC on web view

- install Chrome 65 (canary version) on a Android 7+ device

- enable Webview canary in Android parameters / Developper Options / Webview

- execute the TestWebRTC application on Android

- define the room name to use (default : roomid)

- click on « LOAD VP8 LOOPBACK » to test self WebRTC in VP8 : 
	- AppRTC opens on the following URL : "https://appr.tc/r/roomid?vrc=VP8&debug=loopback&vsc=VP8"
	- click on « Join » => it should work, with user seeing himself

- click on « LOAD H264 LOOPBACK » to test self WebRTC in H264 : 
	- AppRTC opens on the following URL : "https://appr.tc/r/roomid?vrc=H264&debug=loopback&vsc=H264"
	- click on « Join » => this is the issue 801501 (H264 decoding don’t work)

- click on « LOAD P2P » to test AppRTC with a real callee (= not loopback). 
	- AppRTC opens on the following URL : https://appr.tc/r/roomid 
	- The callee must open this URL on his browser. If Callee is on IOS 11 it won’t work, if on Android or Chrome Desktop it should work


