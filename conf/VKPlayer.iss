;This file will be executed next to the application bundle image
;I.e. current directory will contain folder VKPlayer with application files

#include "d:\Idea_workspace\vk_musik_1.0.0\conf\ISC_Constants.txt"

[Setup]
AppId={{Main}}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
AppComments={#MyAppName}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
;DisableStartupPrompt=Yes
;DisableDirPage=Yes
;DisableProgramGroupPage=Yes
;DisableReadyPage=Yes
;DisableFinishedPage=Yes
;DisableWelcomePage=Yes
;Optional License
;LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename={#MyAppName}-{#MyAppVersion}-setup
Compression=lzma
SolidCompression=yes
PrivilegesRequired=admin
SetupIconFile={#MyAppName}\{#MyAppIco}
UninstallDisplayIcon={app}\{#MyAppIco}
UninstallDisplayName={#MyAppName}
WizardImageStretch=No
WizardSmallImageFile={#MyAppName}-setup-icon.bmp   

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "russian"; MessagesFile: "compiler:Languages\Russian.isl"
Name: "ukrainian"; MessagesFile: "compiler:Languages\Ukrainian.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "quicklaunchicon"; Description: "{cm:CreateQuickLaunchIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked; OnlyBelowVersion: 0,6.1

[Files]
Source: "{#MyAppName}\{#MyAppExeName}"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#MyAppName}\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "{#JDKFolder}\jre\lib\ext\*"; DestDir: "{app}\runtime\jre\lib\ext"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\{#MyAppIco}"; Check: returnTrue()
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}";  IconFilename: "{app}\{#MyAppIco}"; Tasks: desktopicon
Name: "{group}\{cm:ProgramOnTheWeb,{#MyAppName}}"; Filename: "{#MyAppURL}"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{userappdata}\Microsoft\Internet Explorer\Quick Launch\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\{#MyAppIco}"; Tasks: quicklaunchicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,VKPlayer}"; Flags: nowait postinstall skipifsilent

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
