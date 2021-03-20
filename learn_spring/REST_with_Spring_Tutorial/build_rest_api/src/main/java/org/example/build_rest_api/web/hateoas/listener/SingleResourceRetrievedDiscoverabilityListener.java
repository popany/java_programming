package org.example.build_rest_api.web.hateoas.listener;

import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

import org.example.build_rest_api.web.hateoas.event.SingleResourceRetrievedEvent;
import org.example.build_rest_api.web.util.LinkUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent> {

    @Override
    public void onApplicationEvent(final SingleResourceRetrievedEvent resourceRetrievedEvent) {
        Preconditions.checkNotNull(resourceRetrievedEvent);

        final HttpServletResponse response = resourceRetrievedEvent.getResponse();
        addLinkHeaderOnSingleResourceRetrieval(response);
    }

    void addLinkHeaderOnSingleResourceRetrieval(final HttpServletResponse response) {
        final String requestURL = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri().toASCIIString();
        final int positionOfLastSlash = requestURL.lastIndexOf("/");
        final String uriForResourceCreation = requestURL.substring(0, positionOfLastSlash);

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, "collection");
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }
}
