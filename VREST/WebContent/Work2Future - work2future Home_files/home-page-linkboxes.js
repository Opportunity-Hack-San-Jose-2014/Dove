jQuery(document).ready(function() {
	jQuery(".linkbox").click(function() {
		var target = jQuery(this);
		var linkbox_source = target.children('.title').children('a:first');
		var link_dest = linkbox_source.attr('href');
		window.location.href = link_dest;
		return false;
	});
});