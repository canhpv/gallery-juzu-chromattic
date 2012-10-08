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
package org.exo.cross.control.servlet;

import java.io.IOException;

import javax.jcr.SimpleCredentials;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.chromattic.api.Chromattic;
import org.exo.cross.model.Document;
import org.exo.cross.model.Model;

/**
 * Created by The eXo Platform SAS
 * Author : Canh Pham Van
 *          canhpv@exoplatform.com
 * Oct 5, 2012  
 */
public class GalleryContext extends Model{

  /** . */
  private HttpServletRequest request;

  /** . */
  private HttpServletResponse response;

  /** . */
  private final String workspaceName;

  public GalleryContext(Chromattic chromattic, String workspaceName, HttpServletRequest request, HttpServletResponse response)
  {    
    
     super(chromattic.openSession(new SimpleCredentials("__system", "[]".toCharArray()), workspaceName));

     //
     this.request = request;
     this.response = response;
     this.workspaceName = workspaceName;
  }
  
  public GalleryContext(Chromattic chromattic, String workspaceName)
  {    
     super(chromattic.openSession(new SimpleCredentials("__system", "[]".toCharArray()), workspaceName));
     this.workspaceName = workspaceName;
  }
  /**
   * Returns the current title. The value is retrieved from the portlet preferences.
   *
   * @return the title
   */
 /* public String getTitle()
  {
     PortletPreferences prefs = request.getPreferences();
     return prefs.getValue("title", "Logomattic");
  }*/

  /**
   * Returns the URL that will invoke the update of the current image with the specified document.
   *
   * @param doc the document to use
   * @return an URL
   */
  public String getUseImageURL(Document doc)
  {
     PortletURL url = ((RenderResponse)response).createActionURL();
     url.setParameter("docid", doc.getId());
     url.setParameter("action", "use");
     return url.toString();
  }

  /**
   * Returns the URL that will invoke the removals of the specified document.
   *
   * @param doc the document to remove
   * @return an URL
   */
  public String getRemoveImageURL(Document doc)
  {
     PortletURL url = ((RenderResponse)response).createActionURL();
     url.setParameter("docid", doc.getId());
     url.setParameter("action", "remove");
     return url.toString();
  }

  /**
   * Returns the URL that will serve the specified document.
   *
   * @param doc the document to serve
   * @return an URL
   */
  public String getImageURL(Document doc)
  {
     String path = doc.getPath();
     return "/rest/jcr/repository/" + workspaceName + path;
  }

  public String getAllImageURL(String url){
    if (url == null)
    {
       url = request.getContextPath() + "/bubbles.png";
    }
    else if (!url.startsWith("http://"))
    {
        Document doc = findDocumentById(url);
        if (doc != null)
        {
            url = getImageURL(doc);
        }
    }
    return url;    
  }
  
  /**
   * Returns the URL of the image to display. The URL is computed using the portlet preferences. When no
   * such preferences exist, then a default URL is returned.
   *
   * @return an URL
   */
/*  public String getImageURL()
  {
     PortletPreferences prefs = request.getPreferences();
     String url = prefs.getValue("url", null);
     if (url == null)
     {
        url = request.getContextPath() + "/bubbles.png";
     }
     else if (!url.startsWith("http://"))
     {
         Document doc = findDocumentById(url);
         if (doc != null)
         {
             url = getImageURL(doc);
         }
     }
     return url;
  }*/

  /**
   * Returns true if the specified document is used as saved logo.
   *
   * @param doc the document to test
   * @return the boolean indicating if the specified document is in use
   */
/*  public boolean isInUse(Document doc)
  {
     PortletPreferences prefs = request.getPreferences();
     String url = prefs.getValue("url", null);
     return doc.getId().equals(url);
  }*/

  /**
   * Saves the specified image in the repository.
   *
   * @param image the image to save
   * @throws IOException any IOException
   */
  public void save(FileItem image) throws IOException
  {
     getRoot().saveDocument(image.getName(), image.getContentType(), image.getInputStream());
     save();
  }
}
