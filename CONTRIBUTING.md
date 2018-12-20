# Process of Creating a Branch, Creating a Pull Request and Merging Everything

## Table of Content
1. [With GitKraken](#GitKraken)
2. [With Visual Studio Code](#Visual-Studio-Code)
3. [With Github](#Github)

## GitKraken
### Steps For Creating A **Branch**
1. Click the *Branch* button on the top middle of the window.
2. Choose the name of your branch.
3. Hit enter.
4. The branch will only be *local* for now. It will be *remote* once you do the first push.

### Steps For Creating A **Pull Request** :
1. Hover the **PULL REQUEST** tag to the left.
2. Click on the + symbol that just appeared.
3. Choose the *From Repo* (in this case **MaxTibs/snqc**).
4. Choose the *branch*. It's the one you were working on.
    * If you are unable to select your branch from here, close the panel on creating a pull request, click the 3 dots symbol that are on you branch and select **Start a pull request to origin/ from origin/branch-name**. This will do it.
5. Choose the *To repo*. Always the same as the *From Repo*
6. Choose the *branch*. Usually you'll want to pick **_master_**.
7. Add a *title* and maybe a little *description*.
8. If you know you will review your code, add him as the *reviewer*.
9. You can also choose to add a *label* to your **PR**.
10. You can finally create your **PULL REQUEST**.

### Steps For Merging A **Pull Request** :
1. Click on the 3 dots and click on *View pull request on github.com* of the **Pull Request** you want to review. It will redirect you to the website of Github (obviously).
2. ALSO, click on the checkout option to test the branch changes. Really important!
3. See [Github steps](#Github) for the rest of the process.

## Visual Studio Code
### Steps For Creating A **Branch**
1. Hit `Ctrl+Shift+P` and write branch in the opened input.
2. Select the command *git: create a branch*
3. Choose the name of your branch.
3. Hit enter.
4. The branch will only be *local* for now. It will be *remote* once you do the first push.

### Steps For Creating A **Pull Request** :
1. To be able to do this in VS code, you'll need [this extension](https://marketplace.visualstudio.com/items?itemName=GitHub.vscode-pull-request-github).
2. Then, you're supposed to see the extension at the bottom of SCM view - Contrôle de code source - located in the left panel.
3. NOTE: You will only be able to see it if there's at least one **Pull request** created ad first.
4. You'll be requiered to sign in to Github to be able to continue.
5. After all that, you will be able to see the extension.
6. Hover the *Github Pull Requests* tab in the SCM view and click the + icon.
7. You will then need to choose the target branch (Typically *master*) the origin branch will be the current checkout one. 

### Steps For Merging A **Pull Request** :
1. Click on the **Pull Request** and then on the *description*.
2. From there, you'll have the possibility to approve the **PR**, *request changes* or *merge the pull request*.
3. You're done!

## Github
### Steps For Creating A **Branch**
1. Go to the home page of the project on github.
2. Click on the *Branch* dropdown.
3. Enter the name of the *branch* you want to create and hit enter.

### Steps For Creating A **Pull Request** :
1. Click the *new pull request* button located on the home page of the project.
2. Change de right branch to be the one you want to create a **pull request** on.
3. Add *title* and *description* and click the green button *Create pull request*.

### Steps For Merging A **Pull Request**
1. First, go the the *Pull requests* tab at the top of the project's page.
2. Select the **Pull request** you want to review.
1. Click either on *Add your review* or the *Files changed* tab to start your review process.
2. There you can add your comments to all files you wish to.
3. Once you are done, still in the *Files changed* tab, click on the *Review changes* green button to the top right.
4. You'll be shown 3 options :
    * One to leave comments without approval.
    * One to approve the PR.
    * One to leave a feedback that must absolutely be taken into account before merging.
5. Once you approve, you will be redirected to the *Conversation tab*. There you will now be able to merge the **Pull Request**. Make sure the first option of the merge type is selected (*Create a merge commit*). This leaves the commits of the branch untouched, so it is easy to revert a commit if ever needed instead of reverting the whole **Pull Request** merge.
6. You can then confirm the merge. No need to deleted the branch as it can be helpful to retrace so changes in the future if we need to.
7. That's it!
