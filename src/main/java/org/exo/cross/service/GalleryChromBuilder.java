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
package org.exo.cross.service;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.exo.cross.model.Content;
import org.exo.cross.model.Directory;
import org.exo.cross.model.Document;
import org.exo.cross.model.File;

/**
 * Created by The eXo Platform SAS
 * Author : Canh Pham Van
 *          canhpv@exoplatform.com
 * Oct 8, 2012  
 */
public class GalleryChromBuilder {

  private String rootNodePath = "/juzugallelry";
  
  private Chromattic chromattic;
  
  public GalleryChromBuilder() {
    ChromatticBuilder builder = ChromatticBuilder.create();
    builder.add(Directory.class);
    builder.add(Content.class);
    builder.add(Document.class);
    builder.add(File.class);

    builder.setOptionValue(ChromatticBuilder.SESSION_LIFECYCLE_CLASSNAME, CurrentRepositoryLifeCycle.class.getName());
    builder.setOptionValue(ChromatticBuilder.CREATE_ROOT_NODE, true);
    builder.setOptionValue(ChromatticBuilder.ROOT_NODE_PATH, rootNodePath);

    chromattic = builder.build();    
  }
  
  public GalleryChromBuilder(String nodePath) {
    ChromatticBuilder builder = ChromatticBuilder.create();
    builder.add(Directory.class);
    builder.add(Content.class);
    builder.add(Document.class);
    builder.add(File.class);

    builder.setOptionValue(ChromatticBuilder.SESSION_LIFECYCLE_CLASSNAME, CurrentRepositoryLifeCycle.class.getName());
    builder.setOptionValue(ChromatticBuilder.CREATE_ROOT_NODE, true);
    builder.setOptionValue(ChromatticBuilder.ROOT_NODE_PATH, nodePath);

    chromattic = builder.build();    
  }  
  
  public Chromattic getChromattic() {
    return chromattic;
  }
}
