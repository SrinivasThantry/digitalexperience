/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.apple.demo.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.EmptyDataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service=Servlet.class,
           property={
                   Constants.SERVICE_DESCRIPTION + "=Servlet for Drop down",
                   "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                   "sling.servlet.paths="+ "/bin/dataSourceServlet"
           })
public class DataSourceServlet extends SlingSafeMethodsServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1397378520502754074L;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException,  IOException {  
         
        List<Resource> themes = new ArrayList<Resource>();  
        // set fallback  
        ResourceResolver resolver = req.getResourceResolver();  
        req.setAttribute(DataSource.class.getName(),  
            EmptyDataSource.instance());  
        ValueMap vm = null;  
        for (int i = 0; i < 5; i++) {  
            // allocate memory to the Map instance  
            vm = new ValueMapDecorator(new HashMap<String, Object>());  
            // Specify the value and text values  
            String Value = "value" + i;  
            String Text = "text" + i;  
            // populate the map  
            vm.put("value", Value);  
            vm.put("text", Text);  
            themes.add(new ValueMapResource(resolver, new ResourceMetadata(),  
                "nt:unstructured", vm));  
        }  
        DataSource dataSource = new SimpleDataSource(themes.iterator());  
        req.setAttribute(DataSource.class.getName(), dataSource); 
    }
}
