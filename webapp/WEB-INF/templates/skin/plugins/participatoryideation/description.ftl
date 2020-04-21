<form onsubmit="return validateForm(this);" action="jsp/site/Portal.jsp"
	method="post">
	<input type="hidden" name="page" value="ideation"> <input
		type="hidden" name="action" value="description">

	<div class="form-group">
		#i18n{participatoryideation.submit_project.steps.description.description_label}
		<textarea id="description_textarea" name="description"
			class="form-control" rows="5"
			maxlength=2000>${form_etape_description.description!''}</textarea>
	</div>

	<div class="form-group">
		<label for="cout">#i18n{participatoryideation.submit_project.steps.description.cost_label}</label>
		<input type="text" class="form-control" name="cout"
			value="${form_etape_description.cout!''}" id="cout"
			style="width: 100px">
	</div>

	<div class="form-group">
		#i18n{participatoryideation.submit_project.steps.description.text1}
		<label class="radio-inline"><input type="radio" name="handicap" id="handicap_yes" value="yes" <#if form_etape_description.handicap?? && form_etape_description.handicap == 'yes'>checked</#if>>Yes</label> 
		<label class="radio-inline"><input type="radio" name="handicap" id="handicap_no"  value="no"  <#if form_etape_description.handicap?? && form_etape_description.handicap == 'no' >checked</#if>>No</label> 
		<div id="handicapComplement_div">
        	<label for="handicapcomplement">#i18n{participatoryideation.submit_project.steps.description.text1_complement}</label>
        	<p>(${i18n("participatoryideation.submit_project.steps.description.text1_validation", 0, 100)})</p>
    		<input type="text" class="form-control" name="handicapComplement" value="${form_etape_description.handicapComplement!''}" id="handicapComplement"/>
		</div>
	</div>



	<div class="form-group">
		<p>
			#i18n{participatoryideation.submit_project.steps.description.text2}
			(${i18n("participatoryideation.submit_project.steps.description.text2_validation", 0, 200)})
		</p>
		<textarea id="field3_textarea" name="field3"
			class="form-control" rows="2">${form_etape_description.field3!''}</textarea>
	</div>

	<div class="form-button text-center">
		<button
			type="submit" value="Continuer" class="btn btn-14rem btn-black-on-white">
			#i18n{participatoryideation.submit_project.steps.description.submit} <i class="fa fa-angle-down"></i>
		</button>
	</div>
</form>

<script>

	function lutece_update_count_description() {
  		var cnt1 = $.trim($("#description_textarea").val()).length;
  		var text = cnt1 + " #i18n{participatoryideation.submit_project.nbChars}";
  		if (cnt1 >= 2) { text += "s"; }
  		text = "(" + text + ")";
  		$("#cnt1").html( text );
	}
	$(function(){
  		$("#description_textarea").after('<p id="cnt1" class="cnt"></p>');
  		lutece_update_count_description();
  		$("#description_textarea").on("input keyup paste cut", lutece_update_count_description);
	});

	function lutece_update_count_field3() {
  		var cnt2 = $.trim($("#field3_textarea").val()).length;
  		var text = cnt2 + " #i18n{participatoryideation.submit_project.nbChars}";
  		if (cnt2 >= 2) { text += "s"; }
  		text = "(" + text + ")";
  		$("#cnt2").html( text );
	}
	$(function(){
  		$("#field3_textarea").after('<p id="cnt2" class="cnt"></p>');
  		lutece_update_count_field3();
  		$("#field3_textarea").on("input keyup paste cut", lutece_update_count_field3);
	});

	function lutece_update_count_handicap() {
  		var cnt3 = $.trim($("#handicapComplement").val()).length;
  		var text = cnt3 + " #i18n{participatoryideation.submit_project.nbChars}";
  		if (cnt3 >= 2) { text += "s"; }
  		text = "(" + text + ")";
  		$("#cnt3").html( text );
	}
	$(function(){
  		$("#handicapComplement").after('<p id="cnt3" class="cnt"></p>');
  		lutece_update_count_handicap();
  		$("#handicapComplement").on("input keyup paste cut", lutece_update_count_handicap);
	});

	$(function(){
		$('#handicap_yes').on( "click", function(e){ 
			$('#handicapComplement_div').show(); 
		} );
		$('#handicap_no' ).on( "click", function(e){ 
			$('#handicapComplement_div').hide(); 
			$('#handicapComplement').val(""); 
			lutece_update_count_handicap();
		} );
		if ($('#handicap_yes').is(":checked")){
			$('#handicapComplement_div').show();
		} else{
			$('#handicapComplement_div').hide();
			$('#handicapComplement').val("");
			lutece_update_count_handicap();
		}
	});

</script>