$(document).ready(function () {

    //default each row to visible
    $('tbody tr').addClass('visible');

    //overrides CSS display:none property
    //so only users w/ JS will see the
    //filter box
    $('#search').show();

    $('#filter').keyup(function (event) {
        //if esc is pressed or nothing is entered
        if (event.keyCode == 27 || $(this).val() == '') {
            //if esc is pressed we want to clear the value of search box
            $(this).val('');

            //we want each row to be visible because if nothing
            //is entered then all rows are matched.
            $('tbody tr').removeClass('visible').show().addClass('visible');
        }

        //if there is text, lets filter
        else {
            filter('tbody tr', $(this).val());
        }

        //reapply zebra rows
        $('.visible td').removeClass('odd');
        zebraRows('.visible:even td', 'odd');
    });

    //grab all header rows
    $('#resultTable thead th').each(function (column) {
        $(this).addClass('sortable')
            .click(function () {
                var findSortKey = function ($cell) {
                    return $cell.find('.sort-key').text().toUpperCase() + ' ' + $cell.text().toUpperCase();
                };

                var sortDirection = $(this).is('.sorted-asc') ? -1 : 1;

                //step back up the tree and get the rows with data
                //for sorting
                var $rows = $(this).parent()
                    .parent()
                    .parent()
                    .find('tbody tr')
                    .get();

                //loop through all the rows and find
                $.each($rows, function (index, row) {
                    row.sortKey = findSortKey($(row).children('td').eq(column));
                });

                //compare and sort the rows alphabetically
                $rows.sort(function (a, b) {
                    if (a.sortKey < b.sortKey) return -sortDirection;
                    if (a.sortKey > b.sortKey) return sortDirection;
                    return 0;
                });

                //add the rows in the correct order to the bottom of the table
                $.each($rows, function (index, row) {
                    $('#resultTable tbody').append(row);
                    row.sortKey = null;
                });

                //identify the column sort order
                $('#resultTable th').removeClass('sorted-asc sorted-desc');
                var $sortHead = $('#resultTable th').filter(':nth-child(' + (column + 1) + ')');
                sortDirection == 1 ? $sortHead.addClass('sorted-asc') : $sortHead.addClass('sorted-desc');

                //identify the column to be sorted by
                $('#resultTable td').removeClass('sorted')
                    .filter(':nth-child(' + (column + 1) + ')')
                    .addClass('sorted');

                $('.visible td').removeClass('odd');
                zebraRows('.visible:even td', 'odd');
            });
    });
});

//filter results based on query
function filter(selector, query) {
    query = $.trim(query); //trim white space
    query = query.replace(/ /gi, '|'); //add OR for regex

    $(selector).each(function () {
        ($(this).text().search(new RegExp(query, "i")) < 0) ? $(this).hide().removeClass('visible') : $(this).show().addClass('visible');
    });
}
function validateForm() {
    var x = document.forms["addSoldierForm"]["queried_soldier_name"].value;
    if (x == null || x == "") {
        alert("Soldier's name must be filled out");
        return false;
    }

    x = document.forms["addSoldierForm"]["queried_unit_name"].value;
    if (x == null || x == "") {
        alert("Unit name must be filled out");
        return false;
    }
    x = document.forms["addSoldierForm"]["queried_soldier_rank"].value;
    if (x == null || x == "") {
        alert("Soldier's rank must be filled out");
        return false;
    }
    x = document.forms["addSoldierForm"]["queried_soldier_birthdate"].value;
    var date_regex = /^([0-9]){4}(-){1}([0-9]){2}(-){1}([0-9]){2}$/;
    if (date_regex.test(x)) {
        var numbers = x.split('-');
        if (numbers[1] > 12) {
            alert("Soldier's birth date must be filled out properly, data must match pattern ('YYYY-MM-DD')");
            return false;
        }
    } else {
        alert("Soldier's birth date must be filled out properly");
        return false;
    }


}