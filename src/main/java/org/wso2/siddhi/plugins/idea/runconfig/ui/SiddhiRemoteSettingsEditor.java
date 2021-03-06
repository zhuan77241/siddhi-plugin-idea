/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.siddhi.plugins.idea.runconfig.ui;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.RawCommandLineEditor;
import org.jetbrains.annotations.NotNull;
import org.wso2.siddhi.plugins.idea.runconfig.SiddhiRunUtil;
import org.wso2.siddhi.plugins.idea.runconfig.remote.SiddhiRemoteConfiguration;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Ui for siddhi remote settings editor.
 */
public class SiddhiRemoteSettingsEditor extends SettingsEditor<SiddhiRemoteConfiguration> {

    private JPanel myPanel;
    private LabeledComponent<RawCommandLineEditor> myParamsField;
    private LabeledComponent<TextFieldWithBrowseButton> myWorkingDirectoryField;
    private LabeledComponent<ModulesComboBox> myModulesComboBox;
    private JPanel myRemoteDebuggingPanel;
    private LabeledComponent<EditorTextField> myHost;
    private LabeledComponent<EditorTextField> myPort;
    private LabeledComponent<TextFieldWithBrowseButton> myFileField;
    private JLabel note;
    private Project myProject;

    public SiddhiRemoteSettingsEditor(Project project) {
        myProject = project;
        SiddhiRunUtil.installSiddhiWithSiddhiFileChooser(project, myFileField.getComponent());
    }

    @Override
    protected void resetEditorFrom(@NotNull SiddhiRemoteConfiguration configuration) {
        myFileField.getComponent().setText(configuration.getFilePath());
        myModulesComboBox.getComponent().setModules(configuration.getValidModules());
        myModulesComboBox.getComponent().setSelectedModule(configuration.getConfigurationModule().getModule());

        myHost.getComponent().setText(configuration.getRemoteDebugHost());
        myPort.getComponent().setText(configuration.getRemoteDebugPort());

        myParamsField.getComponent().setText(configuration.getParams());
        myWorkingDirectoryField.getComponent().setText(configuration.getWorkingDirectory());
    }

    @Override
    protected void applyEditorTo(@NotNull SiddhiRemoteConfiguration configuration)
            throws ConfigurationException {
        configuration.setFilePath(myFileField.getComponent().getText());
        configuration.setModule(myModulesComboBox.getComponent().getSelectedModule());
        configuration.setParams(myParamsField.getComponent().getText());
        configuration.setWorkingDirectory(myWorkingDirectoryField.getComponent().getText());

        configuration.setRemoteDebugHost(myHost.getComponent().getText().trim());
        configuration.setRemoteDebugPort(myPort.getComponent().getText().trim());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myPanel;
    }

    private void createUIComponents() {
        myFileField = new LabeledComponent<>();
        myFileField.setComponent(new TextFieldWithBrowseButton());

        myWorkingDirectoryField = new LabeledComponent<>();
        myWorkingDirectoryField.setComponent(new TextFieldWithBrowseButton());

        myParamsField = new LabeledComponent<>();
        myParamsField.setComponent(new RawCommandLineEditor());

        myModulesComboBox = new LabeledComponent<>();
        myModulesComboBox.setComponent(new ModulesComboBox());

        myHost = new LabeledComponent<>();
        EditorTextField myHostField = new EditorTextField();
        myHostField.setPreferredWidth(300);
        myHost.setComponent(myHostField);

        myPort = new LabeledComponent<>();
        EditorTextField myPortField = new EditorTextField();
        myPortField.setPreferredWidth(100);
        myPort.setComponent(myPortField);
    }
}
