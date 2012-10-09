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

  
  private final String workspaceName;

  public GalleryContext(Chromattic chromattic, String workspaceName, HttpServletRequest request, HttpServletResponse response)
  {    
    
     super(chromattic.openSession(new SimpleCredentials("__system", "[]".toCharArray()), workspaceName));

     this.workspaceName = workspaceName;
  }
  
  public GalleryContext(Chromattic chromattic, String workspaceName)
  {    
     super(chromattic.openSession(new SimpleCredentials("__system", "[]".toCharArray()), workspaceName));
     this.workspaceName = workspaceName;
  }


  public String getImageURL(Document doc)
  {
     String path = doc.getPath();
     return "/rest/jcr/repository/" + workspaceName + path;
  }


  public void save(FileItem image) throws IOException
  {
     getRoot().saveDocument(image.getName(), image.getContentType(), image.getInputStream());
     save();
  }
}
