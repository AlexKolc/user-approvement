(function () {
    var cbstate;
    var pageId = AJS.params.pageId
    window.addEventListener('load', function () {
        getStatesByPageId(pageId)
    });

    function getStatesByPageId(pageId) {
        AJS.$.ajax({
            type: "GET",
            url: "/confluence/rest/approvement/1.0/state/getStatesByPageId",
            contentType: "application/json",
            data: {page_id: pageId},
            async: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown)
            },
            success: function (data) {
                updateCheckboxes(pageId, data)
            }
        })
    }

    function updateCheckboxes(pageId, states) {
        for (var i = 0; i < states.length; i++) {
            var el = document.querySelector('input[name="' + states[i].username + '"]');
            if (el === null) {
                var stateCheckbox = {
                    pageId: pageId,
                    username: states[i].username
                }
                AJS.$.ajax({
                    type: "POST",
                    url: "/confluence/rest/approvement/1.0/state/deleteState",
                    contentType: "application/json",
                    data: JSON.stringify(stateCheckbox),
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log(errorThrown)
                    }
                });
            } else {
                if (states[i].state)
                    el.checked = true;
            }
        }

        var cb = document.getElementsByClassName('save-cb-state');
        for (var i = 0; i < cb.length; i++) {
            cb[i].addEventListener('click', function (evt) {
                if (this.checked) {
                    updateState(pageId, this.name, true)
                } else {
                    updateState(pageId, this.name, false)
                }
            });
        }
    }

    function updateState(pageId, username, state) {
        var stateCheckbox = {
            pageId: pageId,
            username: username,
            state: state
        }
        AJS.$.ajax({
            type: "POST",
            url: "/confluence/rest/approvement/1.0/state/setState",
            contentType: "application/json",
            data: JSON.stringify(stateCheckbox),
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown)
            }
        });
    }

})();