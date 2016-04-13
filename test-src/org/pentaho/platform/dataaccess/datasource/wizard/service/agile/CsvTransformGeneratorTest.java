/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2016 Pentaho Corporation..  All rights reserved.
*/
package org.pentaho.platform.dataaccess.datasource.wizard.service.agile;

import org.junit.Assert;
import org.junit.Test;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaInteger;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.selectvalues.SelectValuesMeta;
import org.pentaho.platform.dataaccess.datasource.wizard.models.ModelInfo;

public class CsvTransformGeneratorTest {

  @Test
  public void testCreateCutLongNamesStep_long() {
    CsvTransformGenerator ctg = new CsvTransformGenerator( new ModelInfo(), null );
    int maxColumnNameLength = 8;
    String stepName = "TEST_STEP_CutLongNames";
    RowMetaInterface fields = new RowMeta();
    String[] fieldNames = new String[] {"a", "b", "A_1", "b_1", "LONGlonglong", "longlonglong_again", "a_2", };
    String[] fieldRenames = new String[] {"a", "b", "A_1", "b_1", "LONGlong", "longlo_1", "a_2"};
    for ( int i = 0; i < fieldNames.length; i++ ) {
      fields.addValueMeta( new ValueMetaInteger( fieldNames[i] ) );
    }
    StepMeta step = ctg.createCutLongNamesStep( fields, maxColumnNameLength, stepName );
    Assert.assertNotNull( "step", step );
    Assert.assertEquals( "step name", stepName, step.getName() );
    StepMetaInterface stepMetaIntegrface = step.getStepMetaInterface();
    Assert.assertNotNull( "stepMetaIntegrface", stepMetaIntegrface );
    Assert.assertTrue( "stepMetaIntegrface instanceof SelectValuesMeta", stepMetaIntegrface instanceof SelectValuesMeta );
    SelectValuesMeta svm = (SelectValuesMeta) stepMetaIntegrface;
    String[] selectName = svm.getSelectName();
    Assert.assertArrayEquals( "selectName", fieldNames, selectName );
    String[] selectRename = svm.getSelectRename();
    Assert.assertArrayEquals( "selectName", fieldRenames, selectRename );
  }

  @Test
  public void testCreateCutLongNamesStep_short() {
    CsvTransformGenerator ctg = new CsvTransformGenerator( new ModelInfo(), null );
    int maxColumnNameLength = 18;
    String stepName = "TEST_STEP_CutLongNames";
    RowMetaInterface fields = new RowMeta();
    String[] fieldNames = new String[] {"a", "b", "A_1", "b_1", "LONGlonglong", "longlonglong_again", "a_2", };
    for ( int i = 0; i < fieldNames.length; i++ ) {
      fields.addValueMeta( new ValueMetaInteger( fieldNames[i] ) );
    }
    StepMeta step = ctg.createCutLongNamesStep( fields, maxColumnNameLength, stepName );
    Assert.assertNull( "step", step );
  }

}
