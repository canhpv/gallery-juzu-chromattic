/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exo.cross.control.juzu.gallery;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.exo.cross.control.servlet.GalleryContext;
import org.exo.cross.model.Content;
import org.exo.cross.model.Directory;
import org.exo.cross.model.Document;
import org.exo.cross.model.File;
import org.exo.cross.service.CurrentRepositoryLifeCycle;
import org.exo.cross.service.GalleryChromBuilder;

import juzu.Controller;
import juzu.Path;
import juzu.View;

/**
 * Created by The eXo Platform SAS
 * Author : Canh Pham Van
 *          canhpv@exoplatform.com
 * Oct 5, 2012  
 */
public class GalleryPortlet extends Controller{
  static Set<String> galleries = new HashSet<String>();
  
  
  @Inject
  @Path("index.gtmpl")
  org.exo.cross.control.juzu.gallery.templates.index index;
  
  
  /** . */
  private Chromattic chromattic;

  /** . */
  private String workspaceName;
  
  private PortletConfig conf;
  
  @View
  public void index() throws PortletException{
    String url = getMapURL();
    index(url);    
  }
  
  @View
  public void index(String url) throws PortletException{
    getAllGalleries();
    index.with().mapURL(url).galleries(galleries).render();
  }
  
  public void init() throws PortletException
  {    
     workspaceName = "portal-system";
     String rootNodePath ="/juzugallelry";
     GalleryChromBuilder galleryBuilder = new GalleryChromBuilder(rootNodePath);
     chromattic = galleryBuilder.getChromattic();
  }  
  
  
  private String getMapURL(){
    StringBuilder b = new StringBuilder();
    String temp = httpContext.getContextPath();
    b.append(httpContext.getScheme()).append("://");
    b.append(httpContext.getServerName()).append(":");
    b.append(httpContext.getServerPort());
    b.append(temp+ "/upload");
    
    return b.toString();
  }   
  
  private void getAllGalleries() throws PortletException{
    init();
    GalleryContext model = new GalleryContext(chromattic, workspaceName);
    StringBuilder b = new StringBuilder();
    String temp = httpContext.getContextPath();
    b.append(httpContext.getScheme()).append("://");
    b.append(httpContext.getServerName()).append(":");
    b.append(httpContext.getServerPort());    
    
    Directory root = model.getRoot();
    for (Document doc : root.getDocuments())
    {
       String imageURL = model.getImageURL(doc);
       galleries.add(b.toString() + imageURL);
    }    
  }
}
