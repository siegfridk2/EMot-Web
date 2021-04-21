<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>EMot Web MPM Test</title>
</head>

<body>

<main class="wrapper" style="padding-top:2em">
	
	<section class="container" id="demo-content">
		
		<div style="border: 5px solid gold;text-align: center;">
			<h1 class="title">MPM QRCode Test For EMotWeb</h1>
		</div>
		
		
		<div id="shield" style="background-color: rgba(0, 0, 0, 0.498039); z-index: 1001; width: 100%; height: 700px; position: fixed; top: 0px; left: 0px; display: none;">
			
			<center>
				<div style="
				    border: 2px soild red;
				    top: 25%;
				    position: absolute;
				    left: 12%;
				    width: 76%;
				    height: 50%;
				">
					<video id="video" style="border: 1px solid gray" width="70%"></video>
					<br><br><input type="button" class="button" id="resetButton" value="Reset"></input>
				</div>
		    </center>

		</div>
		
		
		<center>
		<br>
		<hr>
			<input type="button" class="button" id="startButton" value="Start"></input>
		<br>
		<hr>	
			<div id="sourceSelectPanel" style="display:none">
				<label for="sourceSelect">Change video source:</label>
				<select id="sourceSelect" style="max-width:400px">
				</select>
			</div>
			
			<!-- 
			<div style="border: 10px solid darkred;margin-bottom: 0px;padding-top: 10px;">
				<video id="video" width="300px" height="400px" style="border: 1px solid gray"></video>
			</div>
			 -->
		</center>
		
	</section>
	
</main>

<div id="result_mpm" style="display: none;position: fixed;top: 20%;border: 2px solid goldenrod;width: 100%;text-align: center;background-color: white;z-index:2000;margin: -8px;">
	<br>
	<code id="result"></code>
	<hr>
	<input type="button" class="button" id="close_popup" value="close result">
	<br>
</div>

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/zxing_lib.js"></script>
<script type="text/javascript">
    window.addEventListener('load', function () {
		
    	var shutter_effect = false;
      let selectedDeviceId;
      const codeReader = new ZXing.BrowserMultiFormatReader()
      console.log('ZXing code reader initialized')
      codeReader.listVideoInputDevices()
        .then((videoInputDevices) => {
          const sourceSelect = document.getElementById('sourceSelect')
          selectedDeviceId = videoInputDevices[0].deviceId
          if (videoInputDevices.length >= 1) {
            videoInputDevices.forEach((element) => {
              const sourceOption = document.createElement('option')
              sourceOption.text = element.label
              sourceOption.value = element.deviceId
              sourceSelect.appendChild(sourceOption)
            })

            sourceSelect.onchange = () => {
              selectedDeviceId = sourceSelect.value;
            };

            const sourceSelectPanel = document.getElementById('sourceSelectPanel')
            sourceSelectPanel.style.display = 'block'
          }

          document.getElementById('startButton').addEventListener('click', () => {
            codeReader.decodeFromVideoDevice(selectedDeviceId, 'video', (result, err) => {
            	$("#shield").show();
            	
              if (result) {
                //console.log(result)
                //document.getElementById('result').textContent = result.text
                var audio = new Audio('audio/cam_kasya2.mp3');
                
                var izen_result_text = $("#result").text();
                if (izen_result_text != result.text || izen_result_text == '' || shutter_effect == true){
                	audio.volume = 1;
                	audio.play();
                	
                }
                
                $("#result").text(result.text);
                $("#result_mpm").show();
                shutter_effect = false;
                
              }
              if (err && !(err instanceof ZXing.NotFoundException)) {
                console.error(err)
                document.getElementById('result').textContent = err
              }
            })
            console.log(`Started continous decode from camera with id ${selectedDeviceId}`)
          })
			
          $("#close_popup").click(function(){
        	  $("#result_mpm").hide();
        	  shutter_effect = true;
          });
          document.getElementById('resetButton').addEventListener('click', () => {
        	  $("#shield").hide();
            codeReader.reset()
            document.getElementById('result').textContent = '';
            $("#result_mpm").hide();
            console.log('Reset.')
          })

        })
        .catch((err) => {
          console.log(err)
        })
    })
  </script>

</body>

</html>