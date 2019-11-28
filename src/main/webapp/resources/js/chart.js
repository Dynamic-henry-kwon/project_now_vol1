/**
 * billboard.js를 커스텀한 js소스
 */
	$(document).on('click', '.bb-chart-arcs-title', function() {
			var identifier = $(this).html();
			loadSchedule(identifier);
	});