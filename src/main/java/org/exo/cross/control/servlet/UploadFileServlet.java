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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.chromattic.api.Chromattic;
import org.exo.cross.control.servlet.GalleryContext;

import org.exo.cross.service.GalleryChromBuilder;


/**
 * Created by The eXo Platform SAS
 * Author : Canh Pham Van
 *          canhpv@exoplatform.com
 * Oct 5, 2012  
 */
public class UploadFileServlet extends HttpServlet{
  /**
   * 
   */
  private static final long serialVersionUID = -4168115401340314583L;
  
  /** . */
  private Chromattic chromattic;

  /** . */
  private String workspaceName;
 
  @Override
  public void init() throws ServletException
  {
     workspaceName = parameter("workspace-name");
     String rootNodePath = parameter("root-node-path");
     GalleryChromBuilder galleryBuilder = new GalleryChromBuilder(rootNodePath);
     chromattic = galleryBuilder.getChromattic();
  }  
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    
    try
    {
      GalleryContext model = new GalleryContext(chromattic, workspaceName, request, response);
      
       if (ServletFileUpload.isMultipartContent(request))
       {
          FileItem image = null;
           FileItemFactory factory = new DiskFileItemFactory();
           ServletFileUpload fu = new ServletFileUpload(factory);
           List<FileItem> list = fu.parseRequest(request);
           for (FileItem item : list)
           {
              if (item.getFieldName().equals("photoName"))
              {
                 if (item.getContentType().startsWith("image/"))
                 {
                    image = item;
                    break;
                 }
              }
           }

          //
          if (image != null)
          {
             model.save(image);
          }
       }
      
    } catch (FileUploadException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    StringBuffer buf = new StringBuffer();
    buf.append(request.getScheme()).append("://");
    buf.append(request.getServerName()).append(":");
    buf.append(request.getServerPort());
    buf.append("/ecmdemo");   
    response.sendRedirect(buf.toString());
  }  

  private String parameter(String parameterName) throws ServletException
  {
     String parameter = getServletConfig().getInitParameter(parameterName);
     if (parameter == null)
     {
        throw new ServletException("Servlet is not configured with parameter " + parameterName);
     }
     return parameter.trim();
  }  
}
