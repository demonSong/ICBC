/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    ClusteringDemo.java
 *    Copyright (C) 2009 University of Waikato, Hamilton, New Zealand
 *
 */
package com.demon.ml;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.DensityBasedClusterer;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 * An example class that shows the use of Weka clusterers from Java.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 5578 $
 */
public class Clustering {
  /**
   * Run clusterers
   *
   * @param filename      the name of the ARFF file to run on
   */
  public Clustering(String filename) throws Exception {
    ClusterEvaluation       eval;
    Instances               data;
    DensityBasedClusterer   cl;    

    data = DataSource.read(filename);
    
    cl   = new EM();
    cl.buildClusterer(data);
    
    SerializationHelper.write("data/model/em.model", cl);
    
    eval = new ClusterEvaluation();
    eval.setClusterer(cl);
    
    eval.evaluateClusterer(new Instances(data));
    System.out.println(eval.clusterResultsToString());
  }

  /**
   * usage:
   *   ClusteringDemo arff-file
   */
  public static void main(String[] args) throws Exception {
	  new Clustering("data/process/feature/feature_normal_ICBCtrain.arff");
  }
}
