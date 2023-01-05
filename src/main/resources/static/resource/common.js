$('select[data-value]').each(function(index, el){
	const $el = $(el);

	const defaultValue = $el.attr('data-value').trim();

	if (defaultValue.length > 0){
		$el.val(defaultValue);
	}
})

$('.modal-exam').click(function(){
	$('.layer-bg').show();
	$('.layer').show();
//	$('.layer-bg').css('display', 'block');
})

$('.close-btn').click(function(){
	$('.layer-bg').hide();
	$('.layer').hide();
//	$('.layer-bg').css('display', 'none');
})

$('.layer-bg').click(function(){
	$('.layer-bg').hide();
	$('.layer').hide();
//	$('.layer-bg').css('display', 'none');
})

$('.toggle-btn').click(function(){
	$(this).toggleClass('active');
})

$(".close").click(function() {
	$('.layer-bg').hide();
	$('.layer').hide();
})