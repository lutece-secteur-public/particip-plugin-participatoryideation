<form class="form" onsubmit="return validateForm(this);" action="jsp/site/Portal.jsp" method="post">
	<input type="hidden" name="page" value="ideation">
	<input type="hidden" name="action" value="title">

	<div class="form-group">
		<p>#i18n{participatoryideation.submit_project.steps.title.title_label}</p>
		<input type="text" class="form-control" name="titre" value="${form_etape_title.titre!''}" id="titre" maxlength="#dskey{participatoryideation.site_property.form.titre.maxLength}">
	</div>
	
	<div class="form-group">
		<p>#i18n{participatoryideation.submit_project.steps.title.text1}</strong> (${i18n("participatoryideation.submit_project.steps.title.text1_validation", 0, 200)})</p>
		<input type="text" class="form-control" name="field1" value="${form_etape_title.field1!''}" id="field1">
	</div>
	 
	<div class="form-group">
		<p>#i18n{participatoryideation.submit_project.steps.title.text2} (${i18n("participatoryideation.submit_project.steps.title.text2_validation", 0, 200)})</p>
		<textarea id="field2" name="field2" class="form-control" rows="2">${form_etape_title.field2!''}</textarea>
	</div>
	
  <div class="form-button text-center">
    <button type="submit" value="Continuer" class="btn btn-14rem btn-black-on-white">
      #i18n{participatoryideation.submit_project.steps.title.submit} <i class="fa fa-angle-down"></i>
    </button>
  </div>
</form>

<script>
function lutece_update_count_titre() {
	var cnt = $.trim($("#titre").val()).length;
	var text = cnt + " caract&egrave;re";
	if (cnt >= 2) { text += "s"; }
	text = "(" + text + ")";
	$("#cnt1").html( text );
};
$(function(){
	$("#titre").after('<p id="cnt1" class="cnt"></p>');
	lutece_update_count_titre();
	$("#titre").on("input keyup paste cut", lutece_update_count_titre);
});
	
function lutece_update_count_field1() {
	var cnt = $.trim($("#field1").val()).length;
	var text = cnt + " caract&egrave;re";
	if (cnt >= 2) { text += "s"; }
		text = "(" + text + ")";
		$("#cnt2").html( text );
};
$(function(){
	$("#field1").after('<p id="cnt2" class="cnt"></p>');
	lutece_update_count_field1();
	$("#field1").on("input keyup paste cut", lutece_update_count_field1);
});
	
function lutece_update_count_field2() {
	var cnt = $.trim($("#field2").val()).length;
	var text = cnt + " caract&egrave;re";
	if (cnt >= 2) { text += "s"; }
		text = "(" + text + ")";
		$("#cnt3").html( text );
};
$(function(){
	$("#field2").after('<p id="cnt3" class="cnt"></p>');
	lutece_update_count_field2();
	$("#field2").on("input keyup paste cut", lutece_update_count_field2);
});
</script>