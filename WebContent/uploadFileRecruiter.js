var myfile="";
var counter = 0;

$('#resume_link2').click(function( e ) {
    e.preventDefault();
    $('#resume_recruiter').trigger('click');

});

$('#resume_recruiter').on( 'change', function() {
   myfile= $( this ).val();
   console.log("myfile"+myfile);
       //upload it
       var x = document.getElementById("resume_recruiter");
       var fileNo = "";
       var fileName = "";
       var fileSize = "";
       counter++;
       if ('files' in x)
       {

            for (var i = 0; i < x.files.length; i++)
            {
                fileNo += counter;
                var file = x.files[i];
                if ('name' in file)
                {
                    fileName = file.name;
                }
            }
            var newRow = jQuery('<tr><td>'+fileNo+'</td><td><a href="match_percentage.html?fileNo='+fileNo+'&jobName='+fileName+'" target="_blank">'+fileName+'</a></td></tr>');
            jQuery('tbody.list').append(newRow);

        }

   }
);

console.log(resumeArray[0]);
